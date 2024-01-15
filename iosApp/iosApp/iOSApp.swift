import SwiftUI
import shared

@main
struct iOSApp: App {

    init(){
        KoinKt.doInitKoin(enableNetworkLogs: true)
    }

	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
