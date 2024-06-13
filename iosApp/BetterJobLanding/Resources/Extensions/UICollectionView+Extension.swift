//
//  UICollectionView+Extension.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 07/06/2024.
//

import Foundation
import UIKit

public typealias CollectionViewDelegates = UICollectionViewDataSource & UICollectionViewDelegate & UICollectionViewDelegateFlowLayout

extension UICollectionView {
    func registerForCells(_ strIDs : String...) {
        strIDs.forEach { (strID) in
            register(UINib(nibName: strID, bundle: nil), forCellWithReuseIdentifier: strID)
        }
    }
}

extension UICollectionView {
    func setEmptyMessage(_ message: String) {
            let messageLabel = UILabel(frame: CGRect(x: 0, y: 0, width: self.bounds.size.width, height: self.bounds.size.height))
            messageLabel.text = message
            messageLabel.textColor = .black
            messageLabel.numberOfLines = 0;
            messageLabel.textAlignment = .center;
//            messageLabel.font = APCFont.poppinRegular.as(15)
            messageLabel.sizeToFit()

            self.backgroundView = messageLabel;
        }

        func restore() {
            self.backgroundView = nil
        }
}
