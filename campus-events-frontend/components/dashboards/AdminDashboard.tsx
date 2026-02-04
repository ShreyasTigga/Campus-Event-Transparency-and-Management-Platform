export default function AdminDashboard() {
  return (
    <div className="p-8">
      <h1 className="text-4xl font-bold mb-6">🛡️ Admin Dashboard</h1>

      <div className="grid md:grid-cols-3 gap-6">
        <Card
          title="Pending Approvals"
          desc="Review and approve/reject submitted events"
        />
        <Card
          title="System Overview"
          desc="Monitor users and platform activity"
        />
        <Card
          title="Audit Logs"
          desc="View event approval and enrollment history"
        />
      </div>
    </div>
  );
}

function Card({ title, desc }: { title: string; desc: string }) {
  return (
    <div className="bg-white/10 backdrop-blur-lg p-6 rounded-xl border border-white/10 hover:border-red-500 transition">
      <h2 className="text-xl font-semibold mb-2">{title}</h2>
      <p className="text-white/70">{desc}</p>
    </div>
  );
}
