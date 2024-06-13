//
//  CellStyleOneCollectionViewCell+CollectionView.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 07/06/2024.
//

import Foundation
import UIKit

extension CellStyleOneTableViewCell:UICollectionViewDataSource{
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return 3
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: String(describing: CellStyleOneCollectionViewCell.self), for: indexPath) as? CellStyleOneCollectionViewCell else {
                    return UICollectionViewCell()
                }
                return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        print("tapped \(indexPath.row) \(indexPath.section)")
    }
}

extension CellStyleOneTableViewCell:UICollectionViewDelegate{
    
}

