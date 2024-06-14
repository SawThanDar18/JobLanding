//
//  CellStyleFiveTableViewCell+CollectionView.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 09/06/2024.
//

import Foundation
import UIKit

extension CellStyleFiveTableViewCell:UICollectionViewDataSource{
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return 2
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: String(describing: CellStyleFiveCollectionViewCell.self), for: indexPath) as? CellStyleFiveCollectionViewCell else {
                    return UICollectionViewCell()
                }
                return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        print("tapped \(indexPath.row) \(indexPath.section)")
    }
}


extension CellStyleFiveTableViewCell:UICollectionViewDelegateFlowLayout{
    /// this method is used for flow layout
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        let numberOfItemsPerRow:CGFloat = 2
        let spacingBetweenCells:CGFloat = 16
        // 2 is the value of sectionInset value
        
 
        if let collection = self.cellStyleFiveCollectionView{
            let width = (collection.bounds.width - spacingBetweenCells)/numberOfItemsPerRow
            
            return CGSize(width: width, height: 156)
        }else{
            return CGSize(width: 0, height: 0)
        }
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumLineSpacingForSectionAt section: Int) -> CGFloat {
        return 16
    }
//    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumInteritemSpacingForSectionAt section: Int) -> CGFloat
//    {
//        return 15
//    }

//    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, insetForSectionAt section: Int) -> UIEdgeInsets
//    {
//        return UIEdgeInsets(top: 0, left: 15, bottom: 0, right: 15)
//    }
}
