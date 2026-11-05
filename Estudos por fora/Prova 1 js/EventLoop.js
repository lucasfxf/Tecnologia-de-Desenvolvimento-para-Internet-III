console.log("A");

setTimeout(() => {
  console.log("B");
}, 1000);

console.log("C");

setTimeout(() => {
  console.log("D");
}, 0);

console.log("E");

// -----------------------------------

console.log("A");

setTimeout(() => {
  console.log("B");
}, 0);

console.log("C");

setTimeout(() => {
  console.log("D");
}, 0);

// ------------------------------------

console.log("1");

setTimeout(() => {
  console.log("2");
}, 0);

console.log("3");