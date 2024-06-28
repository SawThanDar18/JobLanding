//
//  UIImage+Extension.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 11/06/2024.
//

import Foundation
import UIKit

extension UIImage {
    func imageWithInsets(insets: UIEdgeInsets) -> UIImage? {
        UIGraphicsBeginImageContextWithOptions(
            CGSize(width: self.size.width + insets.left + insets.right,
                   height: self.size.height + insets.top + insets.bottom), false, self.scale)
        let _ = UIGraphicsGetCurrentContext()
        let origin = CGPoint(x: insets.left, y: insets.top)
        self.draw(at: origin)
        let imageWithInsets = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        return imageWithInsets
    }
    
        func createSelectionIndicator(color: UIColor, size: CGSize, lineWidth: CGFloat) -> UIImage {
            UIGraphicsBeginImageContextWithOptions(size, false, 0)
            color.setFill()
            UIRectFill(CGRectMake(0, 0, size.width, lineWidth))
//            UIRectFill(CGRectMake(0, size.height - lineWidth, size.width, lineWidth))
            let image = UIGraphicsGetImageFromCurrentImageContext()
            UIGraphicsEndImageContext()
            return image ?? UIImage()
        }
    
}


extension UIImageView {
   func setRounded() {
      let radius = CGRectGetWidth(self.frame) / 2
      self.layer.cornerRadius = radius
      self.layer.masksToBounds = false
       self.clipsToBounds = true
   }
    
    public func roundCorners(_ corners: UIRectCorner, radius: CGFloat) {
        let maskPath = UIBezierPath(roundedRect: bounds,
                                    byRoundingCorners: corners,
                                    cornerRadii: CGSize(width: radius, height: radius))
        let shape = CAShapeLayer()
        shape.path = maskPath.cgPath
        layer.mask = shape
    }
}
