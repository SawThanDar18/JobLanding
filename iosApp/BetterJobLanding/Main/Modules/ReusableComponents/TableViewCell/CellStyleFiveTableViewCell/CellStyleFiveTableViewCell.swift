//
//  CellStyleFiveTableViewCell.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 09/06/2024.
//

import UIKit

class CellStyleFiveTableViewCell: UITableViewCell {
    @IBOutlet weak var cellStyleFiveCollectionView: UICollectionView!
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var seeAllButton: UIButton!
    
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
        
        cellStyleFiveCollectionView.dataSource = self
        cellStyleFiveCollectionView.delegate = self
        cellStyleFiveCollectionView.backgroundColor = .clear
        if let layout = cellStyleFiveCollectionView.collectionViewLayout as? UICollectionViewFlowLayout {
            layout.scrollDirection = .horizontal
            layout.minimumLineSpacing = 8
            layout.minimumInteritemSpacing = 0
        }
        cellStyleFiveCollectionView.registerForCells(
            String(describing: CellStyleFiveCollectionViewCell.self)
        )
        cellStyleFiveCollectionView.contentInset = UIEdgeInsets(top: 0, left: 0, bottom: 0, right: 0)
        cellStyleFiveCollectionView.isPagingEnabled = true
        cellStyleFiveCollectionView.isScrollEnabled = false
        cellStyleFiveCollectionView.showsVerticalScrollIndicator = false
        cellStyleFiveCollectionView.showsHorizontalScrollIndicator = false
        
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
