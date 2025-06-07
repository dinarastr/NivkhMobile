import SwiftUI
import KMPNativeBridge

@main
struct iOSApp: App {
    init() {
        KoinHelperKt.doInitKoin()
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
