//
//  CellStyleThreeTableViewCell+CollectionView.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 09/06/2024.
//

import Foundation
import UIKit

extension CellStyleThreeTableViewCell: UICollectionViewDataSource{
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return 5
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: String(describing: CellStyleThreeCollectionViewCell.self), for: indexPath) as? CellStyleThreeCollectionViewCell else {
                    return UICollectionViewCell()
                }
                return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        print("tapped \(indexPath.row) \(indexPath.section)")
    }
}

extension CellStyleThreeTableViewCell:UICollectionViewDelegate{
    
}
