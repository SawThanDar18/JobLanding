//
//  LandingCountryRouter.swift
//  iosApp
//
//  Created by Myint Zu on 04/06/2024.
//

import Foundation

protocol LandingCountryRouting: AnyObject {
    func goToCountrySetter()
}

class LandingCountryRouter: LandingCountryRouting {
    weak var viewController: LandingCountry?
    func goToCountrySetter(){
       print("MAIN")
    }
}

