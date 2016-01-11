class Demo {

    func test() {
        let a = Int("3")
        let b = Int("5")
        let c = Int("7")

        if var a = a, b = b, c = c where c != 0 {
            print("(a + b) / c = \((a + b) / c)")     // (a + b) / c = 5
        }

        if let a = a, b = b, c = c where c != 0 {
            print("(a + b) / c = \((a + b) / c)")     // (a + b) / c = 5
        }

        if let a = a, var b = b, var c = c where c != 0 {
            print("(a + b) / c = \((a + b) / c)")     // (a + b) / c = 5
        }

        if var a = a, var b = b, var c = c where c != 0 {
            print("(a + b) / c = \((a + b) / c)")     // (a + b) / c = 5
        }

        if let a = a, let b = b, let c = c where c != 0 {
            print("(a + b) / c = \((a + b) / c)")     // (a + b) / c = 5
        }

        if let a = a,
         var b = b,
         let c = c where c != 0 {
            print("(a + b) / c = \((a + b) / c)")     // (a + b) / c = 5
        }

        if let a = a,
         b = b,
         let c = c where c != 0 {
            print("(a + b) / c = \((a + b) / c)")     // (a + b) / c = 5
        }
    }

}
