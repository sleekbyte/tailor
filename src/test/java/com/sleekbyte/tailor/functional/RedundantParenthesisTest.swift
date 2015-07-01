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

    for (var i = 0; i < 10; i+=1) {
    }

    do {
        try vend(itemNamed: "Candy Bar")
    } catch (VendingMachineError.InvalidSelection) {
        print("Invalid Selection.")
    }

    throw (VendingMachineError.OutOfStock)

    do {
        try willOnlyThrowIfTrue(false)
    } catch {
        // Handle Error
    }
}
