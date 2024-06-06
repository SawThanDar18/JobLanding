//
//  HomeRouter.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 05/06/2024.
//

import Foundation

protocol HomeRouting: AnyObject {
    func goToScreen()
}

class HomeRouter: HomeRouting {
    weak var viewController: Home?
    func goToScreen(){
       print("GO GO GO")
    }
}

