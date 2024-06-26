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
        let destination = ProfileViewController(nibName: "ProfileViewController", bundle: nil)
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
    
    static func creatNav() -> UINavigationController{
        let nav = UINavigationController(rootViewController: ProfileScene.create())
        nav.isNavigationBarHidden = false
        return nav
    }
    
}
