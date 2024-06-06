//
//  ProfileRouter.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 06/06/2024.
//
import Foundation

protocol ProfileRouting: AnyObject {
    func goToScreen()
}

class ProfileRouter: ProfileRouting {
    weak var viewController: Profile?
    
    func goToScreen(){
       print("GO GO GO")
    }
}
