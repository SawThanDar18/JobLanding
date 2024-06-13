//
//  InboxScene.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 06/06/2024.
//

import Foundation
import UIKit

enum InboxScene {
    static func create() -> UIViewController {
        let st = UIStoryboard(name: "Inbox", bundle: BHRJobLandingConstants.General.bundle)
        let destination = st.instantiateViewController(withIdentifier: "InboxViewController") as! InboxViewController
        //let interactor = GroupListingInteractor()
        let router = InboxRouter()
        //destination.title = "GetStarted"
        //destination.interactor = interactor
        //destination.viewModel = viewModel
        destination.router = router
        router.viewController = destination
        destination.modalPresentationStyle = .fullScreen
        return destination
    }
}
