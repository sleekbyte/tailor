if let movie = item as? Movie {
   print("Movie: '\(movie.name)', dir. \(movie.director)")
}

let trouble = "Objective C"
let moreTrouble = s as AnyObject
let crash = id as! NSDate


let movie = item as! Movie
print("Movie: '\(movie.name)', dir. \(movie.director)")

func someFunction() {
    let someForceCast = NSNumber() as! Int
}

let someForceCast = NSNumber() as? Int

let cell = tableView.dequeueReusableCellWithIdentifier("RecordCell", forIndexPath: indexPath) as! UITableViewCell

let responseDictionary = NSJSONSerialization.JSONObjectWithData(data, options: NSJSONReadingOptions.MutableLeaves, error: &error) as! NSDictionary
let statusCode = responseDictionary["status_code"] as! NSNumber
let statusText = responseDictionary["status_txt"] as! NSString
