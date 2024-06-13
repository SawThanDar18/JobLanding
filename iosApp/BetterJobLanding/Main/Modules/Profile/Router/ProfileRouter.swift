//
//  ProfileRouter.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 06/06/2024.
//
import Foundation

protocol ProfileRouting: AnyObject {
    func goToRegister()
}

class ProfileRouter: ProfileRouting {
    weak var viewController: ProfileViewController?
    
    func goToRegister(){
       print("GO GO GO")
        viewController?.navigationController?.pushViewController(RegisterScene.create(), animated: true)
    }
}
