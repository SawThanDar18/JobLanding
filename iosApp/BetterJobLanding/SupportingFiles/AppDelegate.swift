//
//  AppDelegate.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 04/06/2024.
//

import UIKit
import shared

@main
class AppDelegate: UIResponder, UIApplicationDelegate {
    let screenPortal = ScreenPortalScene.create() as! ScreenPortal
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization after application launch.
        DIHelperKt.doInitKoin(localStorage: IOSLocalStorage(), fileHandler: iOSFileHandler(), assetProvider: IosAssetProvider())
        return true
    }

    func showScreenPortal(){
        UIApplication.shared.windows.first?.rootViewController = screenPortal
        UIApplication.shared.windows.first?.makeKeyAndVisible()
    }
    
    // MARK: UISceneSession Lifecycle

    func application(_ application: UIApplication, configurationForConnecting connectingSceneSession: UISceneSession, options: UIScene.ConnectionOptions) -> UISceneConfiguration {
        // Called when a new scene session is being created.
        // Use this method to select a configuration to create the new scene with.
        return UISceneConfiguration(name: "Default Configuration", sessionRole: connectingSceneSession.role)
    }

    func application(_ application: UIApplication, didDiscardSceneSessions sceneSessions: Set<UISceneSession>) {
        // Called when the user discards a scene session.
        // If any sessions were discarded while the application was not running, this will be called shortly after application:didFinishLaunchingWithOptions.
        // Use this method to release any resources that were specific to the discarded scenes, as they will not return.
    }


}

