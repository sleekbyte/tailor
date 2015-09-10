enum VendingMachineError: ErrorType {
    case InvalidSelection
    case InsufficientFunds(required : Double)
    case OutOfStock
}

func canThrowErrors() throws -> String {
  return ""
}

func cannotThrowErrors() -> String {
  return ""
}

func vend(itemNamed name: String) throws {
    guard var item = inventory[name] else {
        throw VendingMachineError.InvalidSelection
    }

    guard item.count > 0 else {
        throw VendingMachineError.OutOfStock
    }

    if amountDeposited >= item.price {
        // Dispense the snack
        amountDeposited -= item.price
        --item.count
        inventory[name] = item
    } else {
        let amountRequired = item.price - amountDeposited
        throw VendingMachineError.InsufficientFunds(required: amountRequired)
    }
}

func buyFavoriteSnack(person: String) throws {
    let snackName = favoriteSnacks[person] ?? "Candy Bar"
    try vend(itemNamed: snackName)
}

do {
    try vend(itemNamed: "Candy Bar")
    // Enjoy delicious snack
} catch VendingMachineError.InvalidSelection {
    print("Invalid Selection.")
} catch VendingMachineError.OutOfStock {
    print("Out of Stock.")
} catch VendingMachineError.InsufficientFunds(let amountRequired) {
    print("Insufficient funds. Please insert an additional $\(amountRequired).")
}

func willOnlyThrowIfTrue(value: Bool) throws {
    if value { throw someError }
}

do {
    try willOnlyThrowIfTrue(false)
} catch {
    // Handle Error
}

try! willOnlyThrowIfTrue(false)

func processFile(filename: String) throws {
    if exists(filename) {
        let file = open(filename)
        defer {
            close(file)
        }
        while let line = try file.readline() {
            /* Work with the file. */
        }
        // close(file) is called here, at the end of the scope.
    }
}

private func reduceRightToURL(str: String) -> NSURL? {
    if let regex = try? NSRegularExpression(pattern: "(?i)https?://(?:www\\.)?\\S+(?:/|\\b)", options: [.CaseInsensitive]) {
        let nsStr = str as NSString
        let results = regex.matchesInString(str, options: [], range: NSRange(location: 0, length: nsStr.length))
        if let result = results.map({ nsStr.substringWithRange($0.range) }).first, url = NSURL(string: result) {
            return url
        }
    }
    return nil
}
