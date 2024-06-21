//
//  CustomTabBarController.swift
//  iosApp
//
//  Created by Myint Zu on 04/06/2024.
//

import Foundation
import UIKit

class CustomTabBarController: UITabBarController, UIGestureRecognizerDelegate {
    var upperLineView: UIView!
    let spacing: CGFloat = 12
    
    override func viewDidLoad() {
        super.viewDidLoad()
        generateTabBar()
        setTabBarAppearance()
        DispatchQueue.main.asyncAfter(deadline: .now() + 0.2){
            self.addTabbarIndicatorView(index: 0, isFirstTime: true)
        }
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
                viewController: HomeScene.create(viewModel: HomeViewModels()),
                title: "Home",
                image: UIImage(named: "home"),
                selectedImage: UIImage(named: "home_fill")
            ),
            generateVC(
                viewController: ApplicationsScene.create(),
                title: "Applications",
                image: UIImage(named: "applications"),
                selectedImage: UIImage(named: "applications_fill")
            ),
            generateVC(
                viewController: InboxScene.create(),
                title: "Inbox",
                image: UIImage(named: "inbox"),
                selectedImage: UIImage(named: "inbox_fill")
            ),
            generateVC(
                viewController: InterviewsScene.create(),
                title: "Interviews",
                image: UIImage(named: "interviews"),
                selectedImage: UIImage(named: "interviews_fill")
            ),
            generateVC(
                viewController: ProfileScene.create(),
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
            cornerRadius: 0
        )
        
        roundLayer.path = bezierPath.cgPath
        
        //        tabBar.selectionIndicatorImage = UIImage().createSelectionIndicator(color: BHRJobLandingColors.bhrBJPrimary, size: CGSize(width: tabBar.frame.width/CGFloat(tabBar.items!.count), height:  tabBar.frame.height), lineWidth: 1)
        //
        
        tabBar.layer.insertSublayer(roundLayer, at: 0)
        tabBar.itemWidth = width / 5
        tabBar.itemPositioning = .centered
        tabBar.tintColor = BHRJobLandingColors.bhrBJPrimary
        roundLayer.fillColor = UIColor.white.cgColor
        roundLayer.addShadow()
        if #available(iOS 13, *) {
            let appearance = UITabBarAppearance()
            appearance.stackedLayoutAppearance.normal.titleTextAttributes = [NSAttributedString.Key.foregroundColor: #colorLiteral(red: 0.6, green: 0.6, blue: 0.6, alpha: 1),NSAttributedString.Key.font: UIFont.poppinsRegular(ofSize: 12)]
            appearance.stackedLayoutAppearance.selected.titleTextAttributes = [NSAttributedString.Key.foregroundColor: BHRJobLandingColors.bhrBJPrimary,NSAttributedString.Key.font: UIFont.poppinsRegular(ofSize: 12)]
            self.tabBar.standardAppearance = appearance
        } else {
            // For normal state, the color is clear color, so you will not see any title until your tab is selected.
            
            UITabBarItem.appearance().setTitleTextAttributes([NSAttributedString.Key.foregroundColor: #colorLiteral(red: 0.6, green: 0.6, blue: 0.6, alpha: 1),NSAttributedString.Key.font:UIFont.poppinsRegular(ofSize: 12)], for: .normal)
            // Set any color for selected state
            UITabBarItem.appearance().setTitleTextAttributes([NSAttributedString.Key.foregroundColor: BHRJobLandingColors.bhrBJPrimary,NSAttributedString.Key.font:UIFont.poppinsRegular(ofSize: 12)], for: .selected)
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
    
    ///Add tabbar item indicator uper line
    func addTabbarIndicatorView(index: Int, isFirstTime: Bool = false){
        guard let tabView = tabBar.items?[index].value(forKey: "view") as? UIView else {
            return
        }
        if !isFirstTime{
            upperLineView.removeFromSuperview()
        }
        upperLineView = UIView(frame: CGRect(x: tabView.frame.minX + spacing, y: tabView.frame.minY, width: tabView.frame.size.width - spacing * 2, height: 1))
        upperLineView.backgroundColor = BHRJobLandingColors.bhrBJPrimary
        tabBar.addSubview(upperLineView)
    }
    
    func tabBarController(_ tabBarController: UITabBarController, didSelect viewController: UIViewController) {
        addTabbarIndicatorView(index: self.selectedIndex)
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
