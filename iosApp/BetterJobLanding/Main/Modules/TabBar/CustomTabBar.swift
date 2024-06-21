//
//  CustomTabBar.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 21/06/2024.
//
//
//import Foundation
//import UIKit
//
//class CustomTabBar: UITabBar {
//    override func layoutSubviews() {
//        super.layoutSubviews()
//
//        // Find the selection indicator view (UIImageView)
//        if let selectionIndicatorView = subviews.first(where: { $0 is UIImageView && $0.bounds.size.width == self.bounds.width / CGFloat(items?.count ?? 1) }) {
//            // Adjust the position to be at the top
//            var frame = selectionIndicatorView.frame
//            frame.origin.y = -frame.size.height + 20 // Adjust the '10' value as needed
//            selectionIndicatorView.frame = frame
//            bringSubviewToFront(selectionIndicatorView)
//        }
//    }
//}
