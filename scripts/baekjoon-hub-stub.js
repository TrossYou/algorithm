const fs = require("fs");
const { execSync } = require("child_process");

const INDEX_PATH = "index.html";
const EXT_TO_LANG = {
  java: "java",
  py: "python",
  cpp: "cpp",
  js: "js",
  kt: "kotlin",
  c: "c",
};

function sh(cmd) {
  return execSync(cmd, { encoding: "utf8" }).trim();
}

function setOutput(name, value) {
  fs.appendFileSync(process.env.GITHUB_OUTPUT, `${name}<<EOF\n${value}\nEOF\n`);
}

function commitDate() {
  return sh(`git show -s --format=%cd --date=format:%Y-%m-%d HEAD`);
}

function changedFiles() {
  return sh(`git -c core.quotepath=false show --name-only --pretty=format: HEAD`)
    .split("\n")
    .map((s) => s.trim())
    .filter(Boolean);
}

// 프로그래머스/{level}/{번호}. {제목}/{제목}.{ext}
// BaekjoonHub는 번호와 제목 사이에 일반 공백이 아닌 유니코드 공백(U+2005 등)을 씁니다.
const PATH_RE = /^프로그래머스\/(\d+)\/(\d+)\.\s(.+)\/(?:.+)\.(\w+)$/;

function findProblems() {
  const seen = new Set();
  const problems = [];
  for (const file of changedFiles()) {
    const m = file.match(PATH_RE);
    if (!m) continue;
    const [, level, num, title, ext] = m;
    const id = `pg-${num}`;
    if (seen.has(id)) continue;
    seen.add(id);
    problems.push({
      id,
      title,
      level: Number(level),
      lang: EXT_TO_LANG[ext] || "java",
    });
  }
  return problems;
}

function stubEntry(p, date) {
  return `        {
          id: "${p.id}",
          title: "${p.title}",
          url: "https://school.programmers.co.kr/learn/courses/30/lessons/${p.id.slice(3)}",
          codeUrl: "",
          level: ${p.level},
          lang: "${p.lang}",
          tags: [], // TODO: 용어 사전에서 1~3개
          date: "${date}",
          struggled: "solo", // TODO: solo | hint | answer
          cause: null, // TODO: hint/answer면 approach | impl | edge | perf | misread
          idea: "", // TODO
          complexity: "", // TODO
          mistake: "",
          reviews: []
        },\n`;
}

function reviewEntry(date) {
  return `\n          { date: "${date}" }, // TODO: 모드 C — 접근이 달라졌으면 idea/complexity 추가, 같으면 date만 유지`;
}

// id가 이미 있는 문제 = 재풀이. 해당 객체의 reviews: [ ... ] 안에 항목을 끼워넣는다.
function insertReview(html, id, date) {
  const idIdx = html.indexOf(`id: "${id}"`);
  if (idIdx === -1) return html;
  const reviewsKeyIdx = html.indexOf("reviews:", idIdx);
  if (reviewsKeyIdx === -1) {
    throw new Error(`${id} 객체에서 'reviews:' 필드를 찾지 못했습니다.`);
  }
  const openIdx = html.indexOf("[", reviewsKeyIdx);
  if (openIdx === -1) {
    throw new Error(`${id} 객체의 reviews 배열을 찾지 못했습니다.`);
  }
  return (
    html.slice(0, openIdx + 1) + reviewEntry(date) + html.slice(openIdx + 1)
  );
}

function main() {
  const problems = findProblems();
  if (problems.length === 0) {
    setOutput("created", "false");
    return;
  }

  let html = fs.readFileSync(INDEX_PATH, "utf8");
  const date = commitDate();

  const toAdd = problems.filter((p) => !html.includes(`id: "${p.id}"`));
  const toReview = problems.filter((p) => html.includes(`id: "${p.id}"`));

  if (toAdd.length === 0 && toReview.length === 0) {
    setOutput("created", "false");
    return;
  }

  if (toAdd.length > 0) {
    const marker = "const PROBLEMS = [\n";
    const idx = html.indexOf(marker);
    if (idx === -1) {
      throw new Error("index.html에서 'const PROBLEMS = [' 를 찾지 못했습니다.");
    }
    const insertAt = idx + marker.length;
    const stubs = toAdd.map((p) => stubEntry(p, date)).join("");
    html = html.slice(0, insertAt) + stubs + html.slice(insertAt);
  }

  for (const p of toReview) {
    html = insertReview(html, p.id, date);
  }

  fs.writeFileSync(INDEX_PATH, html);

  const all = [...toAdd, ...toReview];
  setOutput("date_short", date.slice(5).replace("-", "")); // YYYY-MM-DD -> MMDD
  setOutput("created", "true");
  setOutput("ids", all.map((p) => p.id).join(", "));
  setOutput("titles", all.map((p) => p.title).join(", "));
  setOutput("branch_suffix", all.map((p) => p.id).join("-"));
  setOutput("new_count", String(toAdd.length));
  setOutput("review_count", String(toReview.length));
}

main();
