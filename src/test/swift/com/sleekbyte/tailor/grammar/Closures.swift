
func backwards(s1: String, s2: String) -> Bool {
    return s1 > s2
}
var reversed = names.sort(backwards)

reversed = names.sort({ (s1: String, s2: String) -> Bool in
    return s1 > s2
})

reversed = names.sort( { (s1: String, s2: String) -> Bool in return s1 > s2 } )

reversed = names.sort( { s1, s2 in return s1 > s2 } )

reversed = names.sort( { s1, s2 in s1 > s2 } )

reversed = names.sort( { $0 > $1 } )

reversed = names.sort(>)

reversed = names.sort() { $0 > $1 }

let strings = numbers.map {
    (number) -> String in
    var output = ""
    while number > 0 {
        output = digitNames[number % 10]! + output
        number /= 10
    }
    return output
}

func makeIncrementer(forIncrement amount: Int) -> (Void) -> Int {
    var runningTotal = 0
    func incrementer() -> Int {
        runningTotal += amount
        return runningTotal
    }
    return incrementer
}

downloader?.downloadImage(URLRequest: URLRequest(.GET, "https://httpbin.org/image/png")) { _, _, _ in
    // No-op
}

let callbackEnabled = SCNetworkReachabilitySetCallback(
    reachability,
    { (_, flags, info) in
        let reachability = Unmanaged<NetworkReachabilityManager>.fromOpaque(info!).takeUnretainedValue()
        reachability.notifyListener(flags)
    },
    &context
)
