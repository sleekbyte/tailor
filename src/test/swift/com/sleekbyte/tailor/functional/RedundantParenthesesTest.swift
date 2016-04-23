func demo() {
    var x: Int
    if (x == 2) {
        return (x < 2)
    }
    else if (y == 2) {

    }

    while(true) {

    }

    repeat {

    } while (true)

    guard (true) else { }

    var x: Int = 2
    switch (x) {
        default:
        break
    }

    do {
        try vend(itemNamed: "Candy Bar")
    } catch (VendingMachineError.InvalidSelection) {
        print("Invalid Selection.")
    }

    throw (VendingMachineError.OutOfStock)

    if (1 * 3) == 4 {
    }

    if a == 2 * (a + b) {
    }

    if ((x + 2) * (y + 4)) == (2 * ((z + 3))) {
    }

    do  {

    } catch a {

    }

    do {

    } catch {

    }

    var shoppingList: [String] = []
    var shoppingList: [String] = ["Eggs", "Milk"]
    var shoppingList: [String] = [("Eggs"), ("Milk")]
    var airports: [String: String] = ["YYZ": "Toronto Pearson", "DUB": "Dublin"]
    var airports: [String: String] = [("YYZ"): ("Toronto Pearson"), ("DUB"): ("Dublin")]
    var namesOfIntegers = [Int: String]()
    namesOfIntegers[16] = "sixteen"
    let checkElementNameLength: (ename: Int, veryLongElementName: Int) = (1, 2)
    var x: Int = (2)
    var y: String = ("Reddit")
    var x = (2)

    var airports = [ (1, 2) , (3, 4) ]
    var airports: [String: (String, String)] = [ "YYZ": ("Toronto", "Toronto Pearson") ]
}

items.map {
  (item) in item.transform()
}

items.map() {
  (item) in item.transform()
}

items.mapWithParams(params) {
  (item) in item.transform()
}

items.map({
  (item) in item.transform()
})

matrix.map {
  (vector) in
    vector.map() {
      (el) in el * el
    }
}
