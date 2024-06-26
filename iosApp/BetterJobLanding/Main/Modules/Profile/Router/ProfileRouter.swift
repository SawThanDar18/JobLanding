//
//  ProfileRouter.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 06/06/2024.
//
import Foundation
import UIKit

protocol ProfileRouting: AnyObject {
    func goToRegister()
}

class ProfileRouter: ProfileRouting {
    weak var viewController: ProfileViewController?
    
    func goToRegister(){
       print("GO GO GO")
//        viewController?.setTabBarHidden(true)
        
        let registerScene = RegisterScene.create()
                
                // Create a UINavigationController with the DetailViewController as the root
                let navController = UINavigationController(rootViewController: registerScene)
                
                // Set the modal presentation style to full screen
                navController.modalPresentationStyle = .fullScreen
                
                // Present the navigation controller
        viewController?.navigationController?.present(navController, animated: true, completion: nil)
        
        
//        viewController?.navigationController?.present(UINavigationController.init(rootViewController: RegisterScene.create()), animated: true)

//        viewController?.navigationController?.pushViewController(RegisterScene.create(), animated: true)
    }
}
