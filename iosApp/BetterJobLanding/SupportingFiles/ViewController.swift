//
//  ViewController.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 04/06/2024.
//

import UIKit
import shared

class ViewController: UIViewController {

    @IBOutlet weak var greetingLabel: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        let greet = Greeting().greets()
        greetingLabel.text = greet
    }


}

