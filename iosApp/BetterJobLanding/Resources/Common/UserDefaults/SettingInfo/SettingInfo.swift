//
//  SettingInfo.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 19/06/2024.
//

import Foundation
import UIKit

struct SettingInfo{
    struct Key {
        static let countryid = "countryid"
        
    }
    
    static var shared = SettingInfo()
    let userDefaults = UserDefaults.standard
    
    var selectedCountryID: String {
        set {
            userDefaults.set(newValue, forKey: Key.countryid)
        }
        
        get {
            return userDefaults.string(forKey: Key.countryid).orEmpty
        }
    }
}
