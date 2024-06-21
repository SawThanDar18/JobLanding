//
//  CellStyleTwoCollectionViewCell+Collection.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 09/06/2024.
//

import Foundation
import UIKit

extension CellStyleTwoTableViewCell:UICollectionViewDataSource{
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        if self.homeUIViewModel[section].collectionType == HomeCollectionType.jobCollection.rawValue{
            return jobsListUIModel.count
        }else if self.homeUIViewModel[section].collectionType == HomeCollectionType.companyCollection.rawValue{
            return companyListModel.count
        }
        return 1
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: String(describing: CellStyleTwoCollectionViewCell.self), for: indexPath) as? CellStyleTwoCollectionViewCell else {
            return UICollectionViewCell()
        }
        cell.renderOfJobsList(jobUIModel: jobsListUIModel[indexPath.row])
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        print("tapped \(indexPath.row) \(indexPath.section)")
    }
}

extension CellStyleTwoTableViewCell:UICollectionViewDelegateFlowLayout{
    /// this method is used for flow layout
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        let numberOfItemsPerRow:CGFloat = 2
        let spacingBetweenCells:CGFloat = 16
        // 2 is the value of sectionInset value
        
 
        if let collection = self.cellStyleTwoCollectionView{
            let width = (collection.bounds.width - spacingBetweenCells)/numberOfItemsPerRow
            
            return CGSize(width: width, height: 71)
        }else{
            return CGSize(width: 0, height: 0)
        }
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumLineSpacingForSectionAt section: Int) -> CGFloat {
        return 16
    }
}
