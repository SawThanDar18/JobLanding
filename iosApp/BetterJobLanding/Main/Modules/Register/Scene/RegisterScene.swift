//
//  RegisterScene.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 12/06/2024.
//

import Foundation
import UIKit

enum RegisterScene {
    static func create() -> UIViewController {
        let st = UIStoryboard(name: "Register", bundle: BHRJobLandingConstants.General.bundle)
        let destination = st.instantiateViewController(withIdentifier: "RegisterViewController") as! RegisterViewController
        //let interactor = GroupListingInteractor()
        let router = RegisterRouter()
        //destination.title = "GetStarted"
        //destination.interactor = interactor
        //destination.viewModel = viewModel
        destination.router = router
        router.viewController = destination
        destination.modalPresentationStyle = .fullScreen
        return destination
    }
}
