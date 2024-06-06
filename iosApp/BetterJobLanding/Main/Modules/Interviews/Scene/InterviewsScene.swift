//
//  InterviewsScene.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 06/06/2024.
//

import Foundation
import UIKit

enum InterviewsScene {
    static func create() -> UIViewController {
        let st = UIStoryboard(name: "Interviews", bundle: BHRJobLandingConstants.General.bundle)
        let destination = st.instantiateViewController(withIdentifier: "Interviews") as! Interviews
        //let interactor = GroupListingInteractor()
        let router = InterviewsRouter()
        //destination.title = "GetStarted"
        //destination.interactor = interactor
        //destination.viewModel = viewModel
        destination.router = router
        router.viewController = destination
        destination.modalPresentationStyle = .fullScreen
        return destination
    }
}
