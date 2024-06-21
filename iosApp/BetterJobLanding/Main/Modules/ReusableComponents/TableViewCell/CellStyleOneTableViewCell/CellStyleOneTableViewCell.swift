//
//  CellStyleOneTableViewCell.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 07/06/2024.
//

import UIKit
import shared

class CellStyleOneTableViewCell: UITableViewCell {
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var seeAllButton: UIButton!
    @IBOutlet weak var cellStyleOneCollectionView: UICollectionView!
    var homeUIViewModel: [HomeUIModel] = []
    var jobsListUIModel : [JobsListUIModel] = []
    var companyListModel: [CollectionCompaniesUIModel] = []

    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        setupCollectionView()
        setupCellStyleOne()
    }
    
    private func setupCellStyleOne(){
        self.titleLabel.font = .poppinsSemiBold(ofSize: 14)
        self.seeAllButton.titleLabel?.font = .poppinsMedium(ofSize: 12)
        self.titleLabel.textColor = BHRJobLandingColors.bhrBJGray6A
        self.seeAllButton.transform = CGAffineTransform(scaleX: -1.0, y: 1.0)
        self.seeAllButton.titleLabel?.transform = CGAffineTransform(scaleX: -1.0, y: 1.0)
        self.seeAllButton.imageView?.transform = CGAffineTransform(scaleX: -1.0, y: 1.0)
    }
    
    private func setupCollectionView() {
        cellStyleOneCollectionView.dataSource = self
        cellStyleOneCollectionView.delegate = self
        cellStyleOneCollectionView.backgroundColor = .clear
        if let layout = cellStyleOneCollectionView.collectionViewLayout as? UICollectionViewFlowLayout {
            layout.scrollDirection = .horizontal
            layout.minimumLineSpacing = 17
            layout.minimumInteritemSpacing = 0
        }
        cellStyleOneCollectionView.registerForCells(
            String(describing: CellStyleOneCollectionViewCell.self)
        )
        cellStyleOneCollectionView.contentInset = UIEdgeInsets(top: 0, left: 0, bottom: 0, right: 0)
//        cellStyleOneCollectionView.isPagingEnabled = true
        cellStyleOneCollectionView.isScrollEnabled = true
        cellStyleOneCollectionView.showsVerticalScrollIndicator = false
        cellStyleOneCollectionView.showsHorizontalScrollIndicator = false
        
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
