//
//  ApplicationsScene.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 06/06/2024.
//

import Foundation
import UIKit

enum ApplicationsScene {
    static func create() -> UIViewController {
        let st = UIStoryboard(name: "Applications", bundle: BHRJobLandingConstants.General.bundle)
        let destination = st.instantiateViewController(withIdentifier: "Applications") as! Applications
        //let interactor = GroupListingInteractor()
        let router = ApplicationsRouter()
        //destination.title = "GetStarted"
        //destination.interactor = interactor
        //destination.viewModel = viewModel
        destination.router = router
        router.viewController = destination
        destination.modalPresentationStyle = .fullScreen
        return destination
    }
}
