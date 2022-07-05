import React, { useEffect, useRef, useState } from 'react';
import Links from './Links';
import styles from './Header.module.css';
import { IoClose, IoMenu } from 'react-icons/io5';

export default function MobileNavigation() {
  const [open, setOpen] = useState(false);
  const ref = useRef<any>();

  useEffect(() => {
    const closeDropdown = (e: any )=> {
      if(!ref.current.contains(e.target)) {
        setOpen(false);
      }
    }
    document.addEventListener('mousedown', closeDropdown)

    return () => document.removeEventListener('mousedown', closeDropdown)
  }, [open])

  const openIcon = (
    <IoMenu
      className={styles.burger}
      onClick={() => setOpen(!open)}
      size="25px"
      color="#393939"
    />
  );

  const closeIcon = (
    <IoClose
      className={styles.burger}
      onClick={() => setOpen(!open)}
      size="25px"
      color="#393939"
    />
  );

  return (
    <div className={styles.mobileNavigation} ref={ref}>
      {open ? closeIcon : openIcon}
      {open && <Links closeMenu={setOpen}/>}
    </div>
  );
}
