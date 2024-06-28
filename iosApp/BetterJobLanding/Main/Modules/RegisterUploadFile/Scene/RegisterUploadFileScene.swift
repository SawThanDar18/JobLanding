//
//  RegisterUploadFileUploadFileScene.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 26/06/2024.
//

import Foundation
import UIKit

enum RegisterUploadFileScene {
    static func create() -> UIViewController {
        let destination = RegisterUploadFileViewController(nibName: "RegisterUploadFileViewController", bundle: nil)
        //let interactor = GroupListingInteractor()
        let router = RegisterUploadFileRouter()
        //destination.title = "GetStarted"
        //destination.interactor = interactor
        //destination.viewModel = viewModel
        destination.router = router
        router.viewController = destination
        destination.modalPresentationStyle = .fullScreen
        return destination
    }
}
