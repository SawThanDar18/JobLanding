//
//  CellStyleTwoTableViewCell.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 07/06/2024.
//

import UIKit

class CellStyleTwoTableViewCell: UITableViewCell {
    @IBOutlet weak var cellStyleTwoCollectionView: UICollectionView!
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
        
        cellStyleTwoCollectionView.dataSource = self
        cellStyleTwoCollectionView.delegate = self
        cellStyleTwoCollectionView.backgroundColor = .clear
        if let layout = cellStyleTwoCollectionView.collectionViewLayout as? UICollectionViewFlowLayout {
            layout.scrollDirection = .horizontal
            layout.minimumLineSpacing = 0
            layout.minimumInteritemSpacing = 0
        }
        cellStyleTwoCollectionView.registerForCells(
            String(describing: CellStyleTwoCollectionViewCell.self)
        )
        cellStyleTwoCollectionView.contentInset = UIEdgeInsets(top: 0, left: 0, bottom: 0, right: 0)
        cellStyleTwoCollectionView.isPagingEnabled = true
        cellStyleTwoCollectionView.isScrollEnabled = false
        cellStyleTwoCollectionView.showsVerticalScrollIndicator = false
        cellStyleTwoCollectionView.showsHorizontalScrollIndicator = false
        
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
