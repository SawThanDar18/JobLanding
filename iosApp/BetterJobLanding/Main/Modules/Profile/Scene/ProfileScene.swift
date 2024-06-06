//
//  ProfileScene.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 06/06/2024.
//

import Foundation
import UIKit

enum ProfileScene {
    static func create() -> UIViewController {
        let st = UIStoryboard(name: "Profile", bundle: BHRJobLandingConstants.General.bundle)
        let destination = st.instantiateViewController(withIdentifier: "Profile") as! Profile
        //let interactor = GroupListingInteractor()
        let router = ProfileRouter()
        //destination.title = "GetStarted"
        //destination.interactor = interactor
        //destination.viewModel = viewModel
        destination.router = router
        router.viewController = destination
        destination.modalPresentationStyle = .fullScreen
        return destination
    }
}
