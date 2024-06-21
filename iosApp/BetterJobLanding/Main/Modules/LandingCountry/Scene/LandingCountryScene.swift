//
//  LandingCountryScene.swift
//  iosApp
//
//  Created by Myint Zu on 04/06/2024.
//

import Foundation
import UIKit

enum LandingCountryScene {
    static func create() -> UIViewController {
        let st = UIStoryboard(name: "LandingCountry", bundle: BHRJobLandingConstants.General.bundle)
        let destination = st.instantiateViewController(withIdentifier: "LandingCountry") as! LandingCountry
        //let interactor = GroupListingInteractor()
        let router = LandingCountryRouter()
        //destination.title = "GetStarted"
        //destination.interactor = interactor
//        destination.viewModel = viewModel
        destination.router = router
        router.viewController = destination
        destination.modalPresentationStyle = .fullScreen
        return destination
    }
}
