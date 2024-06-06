//
//  InterviewsRouter.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 06/06/2024.
//

import Foundation

protocol InterviewsRouting: AnyObject {
    func goToScreen()
}

class InterviewsRouter: InterviewsRouting {
    weak var viewController: Interviews?
    
    func goToScreen(){
       print("GO GO GO")
    }
}
