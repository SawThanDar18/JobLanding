//
//  Fonts.swift
//  iosApp
//
//  Created by Myint Zu on 04/06/2024.
//

import Foundation
import UIKit

extension UIFont {
    
    public class func poppinsLight(ofSize size: CGFloat) -> UIFont {
        guard let font = UIFont(name: "Poppins-Light", size: size) else {
            return UIFont.systemFont(ofSize: size, weight: .semibold)
        }
        return font
    }
    public class func poppinsRegular(ofSize size: CGFloat) -> UIFont {
        guard let font = UIFont(name: "Poppins-Regular", size: size) else {
            return UIFont.systemFont(ofSize: size, weight: .regular)
        }
        return font
    }
    
    public class func poppinsMedium(ofSize size: CGFloat) -> UIFont {
        guard let font = UIFont(name: "Poppins-Medium", size: size) else {
            return UIFont.systemFont(ofSize: size, weight: .medium)
        }
        return font
    }
    
    public class func poppinsSemiBold(ofSize size: CGFloat) -> UIFont {
        guard let font = UIFont(name: "Poppins-SemiBold", size: size) else {
            return UIFont.systemFont(ofSize: size, weight: .semibold)
        }
        return font
    }
  
}

