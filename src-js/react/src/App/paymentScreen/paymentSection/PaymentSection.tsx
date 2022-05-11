import React, { useEffect, useState } from 'react';
import styles from './PaymentSection.module.css';

const MainSection: React.FC = () => {
  return (
    <div className={styles.paymentBox}>
      <div>
        <button>payment</button>
      </div>
    </div>
  );
};

export default MainSection;
