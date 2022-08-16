import React from 'react'
import './Modal.css';

export default function Modal({active, setActive, children}: {active: any, setActive: any, children: any}) {
  return (
    <div className={active ? "modal modalActive" : "modal"} onClick={() => setActive(false)}>
        <div className={active ? "modalContent modalContentActive" : "modalContent"} onClick={e => e.stopPropagation()}>
            {children}
        </div>
    </div>
  )
}
