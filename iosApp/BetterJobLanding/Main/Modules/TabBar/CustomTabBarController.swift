//
//  CustomTabBarController.swift
//  iosApp
//
//  Created by Myint Zu on 04/06/2024.
//

import Foundation
import UIKit

class CustomTabBarController: UITabBarController, UIGestureRecognizerDelegate {
  
    override func viewDidLoad() {
        super.viewDidLoad()
        generateTabBar()
        setTabBarAppearance()
        delegate = self
    }
    
    func gestureRecognizer(_ gestureRecognizer: UIGestureRecognizer, shouldReceive touch: UITouch) -> Bool {
        if ((touch.view?.isDescendant(of: self.view)) != nil){
              return false
          }
          return true
      }

    private func generateTabBar() {
        viewControllers = [
            generateVC(
                viewController: HomeScene.create(),
                title: "Home",
                image: UIImage(named: "home"),
                selectedImage: UIImage(named: "home_fill")
            ),
            generateVC(
                viewController: HomeScene.create(),
                title: "Applications",
                image: UIImage(named: "applications"),
                selectedImage: UIImage(named: "applications_fill")
            ),
            generateVC(
                viewController: HomeScene.create(),
                title: "Inbox",
                image: UIImage(named: "inbox"),
                selectedImage: UIImage(named: "inbox_fill")
            ),
            generateVC(
                viewController: HomeScene.create(),
                title: "Interviews",
                image: UIImage(named: "interviews"),
                selectedImage: UIImage(named: "interviews_fill")
            ),
            generateVC(
                viewController: HomeScene.create(),
                title: "Profile",
                image: UIImage(named: "profile"),
                selectedImage: UIImage(named: "profile_fill")
            ),
        ]
    }
    
    
    private func generateVC(viewController: UIViewController, title: String, image: UIImage?, selectedImage: UIImage?) -> UIViewController {
        viewController.tabBarItem.title = title
        viewController.tabBarItem.image = image
        viewController.tabBarItem.selectedImage = selectedImage
        return viewController
    }
    
    private func setTabBarAppearance() {
        let positionOnX: CGFloat = 0
        let positionOnY: CGFloat = 14
        let width = tabBar.bounds.width - positionOnX * 2
        let height = tabBar.bounds.height + positionOnY * 5
        
        let roundLayer = CAShapeLayer()
        
        let bezierPath = UIBezierPath(
            roundedRect: CGRect(
                x: positionOnX,
                y: tabBar.bounds.minY - positionOnY,
                width: width,
                height: height
            ),
            cornerRadius: 15
        )
        
        roundLayer.path = bezierPath.cgPath
        tabBar.backgroundColor = .white
        tabBar.layer.insertSublayer(roundLayer, at: 0)
        tabBar.itemWidth = width / 5
        tabBar.itemPositioning = .centered
        tabBar.tintColor = BHRJobLandingColors.bhrBJPrimary
        roundLayer.fillColor = UIColor.white.cgColor
        roundLayer.addShadow()
        if #available(iOS 13, *) {
            let appearance = UITabBarAppearance()
//            appearance.stackedLayoutAppearance.normal.titleTextAttributes = [NSAttributedString.Key.foregroundColor: UIColor.black,NSAttributedString.Key.font: APCFont.menuTitle]
//            appearance.stackedLayoutAppearance.selected.titleTextAttributes = [NSAttributedString.Key.foregroundColor: APCColors.aplusSecondary,NSAttributedString.Key.font: APCFont.menuTitle]
            self.tabBar.standardAppearance = appearance
        } else {
            // For normal state, the color is clear color, so you will not see any title until your tab is selected.
//            UITabBarItem.appearance().setTitleTextAttributes([NSAttributedString.Key.foregroundColor: UIColor.clear,NSAttributedString.Key.font:APCFont.menuTitle], for: .normal)
//            // Set any color for selected state
//            UITabBarItem.appearance().setTitleTextAttributes([NSAttributedString.Key.foregroundColor: APCColors.aplusSecondary,NSAttributedString.Key.font:APCFont.menuTitle], for: .selected)
        }
    }
}

// MARK: - UITabBarController Delegate
extension CustomTabBarController: UITabBarControllerDelegate {
    func tabBarController(_ tabBarController: UITabBarController, shouldSelect viewController: UIViewController) -> Bool {
        guard let selectedIndex = tabBarController.viewControllers?.firstIndex(of: viewController) else {
            return true
        }
        
        return true
    }
}

extension CustomTabBarController: UIPopoverPresentationControllerDelegate {
    func adaptivePresentationStyle(for controller: UIPresentationController) -> UIModalPresentationStyle {
        return  .none
    }
}


extension CAShapeLayer {
    func addShadow() {
        shadowColor = UIColor.lightGray.cgColor
        shadowOffset = .zero
        shadowOpacity = 0.5
        shadowRadius = 7
    }
}
