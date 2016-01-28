reversed = names.sort { s1, s2 in return s1 > s2 }

reversed = names.sort({ s1, s2 in return s1 > s2 })

reversed = names.sort(["John", "Jack"], { s1, s2 in return s1 > s2 })

reversed = names.sort(["John", "Jack"]) { s1, s2 in return s1 > s2 }

reversed = names.sort(["John", "Jack"], { s1, s2 in return s1 > s2 }, false)

reversed = names.sort(["John", "Jack"], { s in return s + s },
  { s1, s2 in return s1 > s2 })

reversed = names.sort(["John", "Jack"], { s in return s + s }) { s1, s2 in return s1 > s2 }

reversed = names.sort(comparator: { s1, s2 in return s1 > s2 })
