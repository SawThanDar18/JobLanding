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
//
////Indicator
//enum Side: String {
//    case top, left, right, bottom
//}
//
//extension UIImage {
//    func createSelectionIndicator(color: UIColor, size: CGSize, lineThickness: CGFloat, side: Side) -> UIImage {
//        var xPosition = 0.0
//        var yPosition = 0.0
//        var imgWidth = 2.0
//        var imgHeight = 2.0
//        switch side {
//        case .top:
//            xPosition = -50
//            yPosition = 0.0
//            imgWidth = size.width
//            imgHeight = lineThickness
//        case .bottom:
//            xPosition = 0.0
//            yPosition = size.height - lineThickness
//            imgWidth = size.width
//            imgHeight = lineThickness
//        case .left:
//            xPosition = 0.0
//            yPosition = 0.0
//            imgWidth = lineThickness
//            imgHeight = size.height
//        case .right:
//            xPosition = size.width - lineThickness
//            yPosition = 0.0
//            imgWidth = lineThickness
//            imgHeight = size.height
//        }
//        UIGraphicsBeginImageContextWithOptions(size, false, 0)
//        color.setFill()
//        UIRectFill(CGRect(x: xPosition, y: yPosition, width: imgWidth, height: imgHeight))
//        let image = UIGraphicsGetImageFromCurrentImageContext()
//        UIGraphicsEndImageContext()
//        return image!
//    }
//}
