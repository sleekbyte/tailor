reversed = names.sort { s1, s2 in return s1 > s2 }

reversed = names.sort({ s1, s2 in return s1 > s2 })

reversed = names.sort(["John", "Jack"], { s1, s2 in return s1 > s2 })

reversed = names.sort(["John", "Jack"]) { s1, s2 in return s1 > s2 }

reversed = names.sort(["John", "Jack"], { s1, s2 in return s1 > s2 }, false)

reversed = names.sort(["John", "Jack"], { s in return s + s },
  { s1, s2 in return s1 > s2 })

reversed = names.sort(["John", "Jack"], { s in return s + s }) { s1, s2 in return s1 > s2 }

reversed = names.sort(comparator: { s1, s2 in return s1 > s2 })

names.something()

names.convert(toUpperCase)

print(1 + 2 + 4 + 5)

guard let x = x.filter({ return $0.id == someString }).first else {
    return
}

if y.contains({ return $0.id == someString }) {}

let x = x.filter({ return $0.id == someString }).first

let x = x.filter { return $0.id == someString }.first
