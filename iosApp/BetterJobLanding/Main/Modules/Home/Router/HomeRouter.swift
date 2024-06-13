//
//  HomeRouter.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 05/06/2024.
//

import Foundation

protocol HomeRouting: AnyObject {
    func goToRegister()
}

class HomeRouter: HomeRouting {
    weak var viewController: HomeViewController?
    
    func goToRegister(){
        viewController?.navigationController?.pushViewController(RegisterScene.create(), animated: true)
    }
}

