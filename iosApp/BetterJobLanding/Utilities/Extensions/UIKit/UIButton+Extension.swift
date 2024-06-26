//
//  UIButton+Extension.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 24/06/2024.
//

import Foundation
import UIKit

extension UIButton{
    
    func setButtonDisableStyle(radius: Int = 8){
        self.backgroundColor = BHRJobLandingColors.bhrBJDisable
        self.titleLabel?.font = .poppinsSemiBold(ofSize: 14)
        self.isUserInteractionEnabled = false
        self.titleLabel?.textColor = UIColor.white
        self.layer.cornerRadius = CGFloat(radius)
    }
    
    func setButtonActiveStyle(radius: Int = 8){
        self.backgroundColor = BHRJobLandingColors.bhrBJPrimary
        self.titleLabel?.font = .poppinsSemiBold(ofSize: 14)
        self.isUserInteractionEnabled = true
        self.titleLabel?.textColor = UIColor.white
        self.layer.cornerRadius = CGFloat(radius)
    }
}
