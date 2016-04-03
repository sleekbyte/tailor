// TODO(dev-name):Implement SomeClass
class SomeClass {

    // todo: Implement someMethod
    func someMethod() {
        // ToDo Remove comment after implementing someMethod
    }

    // this is an invalid comment because it contains TODO:todo Todo: words



    /* Bug: return positive integer
     */
    func getPositiveNumber() -> Int {
        // TODO: Valid single line todo comment

        /* multi-line
         comment */

        return -1
    }

    // TODO(aditya): this is a valid TODO syntax


    // TODO : invalid

    // TODO(): invalid

    /// TODO: documentation comments should not have tasks to be done

    // // TODO: Nested single line comments will get flagged.

    /// // TODO:Nested single line comments will get flagged.

    // comment with // nested internal todo comment will get flagged

    // comment that ends with // TODO: do flag

    // Todorov should not be flagged
    // Mentioning a todo in a comment should be flagged
}
