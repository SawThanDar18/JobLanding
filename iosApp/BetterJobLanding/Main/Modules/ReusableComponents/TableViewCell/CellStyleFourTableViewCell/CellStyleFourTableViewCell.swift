//
//  cellStyleFourCollectionView.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 09/06/2024.
//

import UIKit
import shared

class CellStyleFourTableViewCell: UITableViewCell {

    @IBOutlet weak var cellStyleFourCollectionView: UICollectionView!
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var seeAllButton: UIButton!
    var homeUIViewModel: [HomeUIModel] = []
//    var jobsListUIModel : [JobsListUIModel] = []
    var companyListModel: [CollectionCompaniesUIModel] = []
    
    override func awakeFromNib() {
        super.awakeFromNib()
        setupCollectionView()
    }
    
    private func setupCollectionView() {
        self.seeAllButton.transform = CGAffineTransform(scaleX: -1.0, y: 1.0)
        self.seeAllButton.titleLabel?.transform = CGAffineTransform(scaleX: -1.0, y: 1.0)
        self.seeAllButton.imageView?.transform = CGAffineTransform(scaleX: -1.0, y: 1.0)
        
        self.titleLabel.font = .poppinsSemiBold(ofSize: 14)
        self.seeAllButton.titleLabel?.font = .poppinsMedium(ofSize: 12)
        self.titleLabel.textColor = BHRJobLandingColors.bhrBJGray6A
        
        cellStyleFourCollectionView.dataSource = self
        cellStyleFourCollectionView.delegate = self
        cellStyleFourCollectionView.backgroundColor = .clear
        if let layout = cellStyleFourCollectionView.collectionViewLayout as? UICollectionViewFlowLayout {
            layout.scrollDirection = .horizontal
            layout.minimumLineSpacing = 0
            layout.minimumInteritemSpacing = 0
        }
        cellStyleFourCollectionView.registerForCells(
            String(describing: CellStyleFourCollectionViewCell.self)
        )
        cellStyleFourCollectionView.contentInset = UIEdgeInsets(top: 0, left: 0, bottom: 0, right: 0)
        cellStyleFourCollectionView.isPagingEnabled = true
        cellStyleFourCollectionView.isScrollEnabled = false
        cellStyleFourCollectionView.showsVerticalScrollIndicator = false
        cellStyleFourCollectionView.showsHorizontalScrollIndicator = false
        
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
