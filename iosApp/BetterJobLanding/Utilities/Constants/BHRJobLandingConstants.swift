//
//  BHRJobLandingConstants.swift
//  iosApp
//
//  Created by Myint Zu on 04/06/2024.
//

import Foundation

class BHRJobLandingConstants: NSObject {
    
    public class General {
        static var bundle : Bundle {
            get {
                return Bundle(identifier: "co.better.hr")!
            }
        }
    }
    
    struct Key {
        static let getStarted = "getStarted"
    }
    
    static var shared = BHRJobLandingConstants()
    let userDefaults = UserDefaults.standard
    
    var getStarted: Bool {
        set {
            userDefaults.set(newValue, forKey: Key.getStarted)
        }
        
        get {
            return userDefaults.bool(forKey: Key.getStarted)
        }
    }
}
