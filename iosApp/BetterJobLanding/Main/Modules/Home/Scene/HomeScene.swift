//
//  HomeScene.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 05/06/2024.
//

import Foundation
import UIKit

enum HomeScene {
    static func create(viewModel: HomeViewModels) -> UIViewController {
        let st = UIStoryboard(name: "Home", bundle: BHRJobLandingConstants.General.bundle)
        let destination = st.instantiateViewController(withIdentifier: "Home") as! HomeViewController
        //let interactor = GroupListingInteractor()
        let router = HomeRouter()
        //destination.title = "GetStarted"
        //destination.interactor = interactor
        destination.viewModel = viewModel
        destination.router = router
        router.viewController = destination
        destination.modalPresentationStyle = .fullScreen
        return destination
    }
    
}
