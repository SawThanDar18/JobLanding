//
//  CellStyleSixTableViewCell.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 09/06/2024.
//

import UIKit

class CellStyleSixTableViewCell: UITableViewCell {
    @IBOutlet weak var cellStyleSixCollectionView: UICollectionView!
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
        
        cellStyleSixCollectionView.dataSource = self
        cellStyleSixCollectionView.delegate = self
        cellStyleSixCollectionView.backgroundColor = .clear
        if let layout = cellStyleSixCollectionView.collectionViewLayout as? UICollectionViewFlowLayout {
            layout.scrollDirection = .horizontal
            layout.minimumLineSpacing = 8
            layout.minimumInteritemSpacing = 0
        }
        cellStyleSixCollectionView.registerForCells(
            String(describing: CellStyleSixCollectionViewCell.self)
        )
        cellStyleSixCollectionView.contentInset = UIEdgeInsets(top: 0, left: 0, bottom: 0, right: 0)
//        cellStyleSixCollectionView.isPagingEnabled = true
        cellStyleSixCollectionView.isScrollEnabled = false
        cellStyleSixCollectionView.showsVerticalScrollIndicator = false
        cellStyleSixCollectionView.showsHorizontalScrollIndicator = false
        
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
