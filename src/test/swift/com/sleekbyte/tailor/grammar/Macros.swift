extension NSURLSessionConfiguration {
    static func backgroundSessionConfigurationForAllPlatformsWithIdentifier(identifier: String) -> NSURLSessionConfiguration {
        let configuration: NSURLSessionConfiguration

        #if os(iOS) || os(watchOS)
            configuration = NSURLSessionConfiguration.backgroundSessionConfigurationWithIdentifier(identifier)
        #else
            if #available(OSX 10.10, *) {
                configuration = NSURLSessionConfiguration.backgroundSessionConfigurationWithIdentifier(identifier)
            } else {
                configuration = NSURLSessionConfiguration.backgroundSessionConfiguration(identifier)
            }
        #endif

        return configuration
    }
}

#if !os(watchOS)

@available(iOS 9.0, OSX 10.11, *)
extension Manager {
    private enum Streamable {
        case Stream(String, Int)
        case NetService(NSNetService)
    }

    private func stream(streamable: Streamable) -> Request {
        var streamTask: NSURLSessionStreamTask!

        switch streamable {
        case .Stream(let hostName, let port):
            dispatch_sync(queue) {
                streamTask = self.session.streamTaskWithHostName(hostName, port: port)
            }
        case .NetService(let netService):
            dispatch_sync(queue) {
                streamTask = self.session.streamTaskWithNetService(netService)
            }
        }

        let request = Request(session: session, task: streamTask)

        delegate[request.delegate.task] = request.delegate

        if startRequestsImmediately {
            request.resume()
        }

        return request
    }
}

#endif

self.totalBytes = {
    #if os(iOS) || os(watchOS)
        let size = CGSize(width: image.size.width * image.scale, height: image.size.height * image.scale)
    #elseif os(OSX)
        let size = CGSize(width: image.size.width, height: image.size.height)
    #endif

    let bytesPerPixel: CGFloat = 4.0
    let bytesPerRow = size.width * bytesPerPixel
    let totalBytes = UInt64(bytesPerRow) * UInt64(size.height)

    return totalBytes
}()
